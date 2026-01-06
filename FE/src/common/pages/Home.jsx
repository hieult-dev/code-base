import { useEffect, useState } from 'react'
import { fetchCourses } from '@/apps/course/api/course-api-rest';
import '@/common/pages/assets/Home.css'
import CourseCard from '@/apps/course/components/CourseCard';
import { useUserStore } from '@/common/store/user';

export default function Home() {
    const [courses, setCourses] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)
    const isRefreshingToken = useUserStore(
        (state) => state.isRefreshingToken
    );
    useEffect(() => {
        const loadCourses = async () => {
            try {
                const response = await fetchCourses()
                setCourses(response)
            } catch (err) {
                console.error(err)
                setError('Không load được course')
            } finally {
                setLoading(false)
            }
        }

        loadCourses()
    }, [])

    if (loading || isRefreshingToken) {
        return <p>Loading courses...</p>;
    }

    if (error && !isRefreshingToken) {
        return <p style={{ color: 'red' }}>{error}</p>;
    }

    return (
        <div className="home">
            <h1 className="page-title">Courses</h1>
            <div className="course-grid">
                {Array.isArray(courses) &&
                    courses.map((course) => {
                        return (
                            <CourseCard
                                key={course.id}
                                course={course}
                            />
                        )
                    })
                }
            </div>
        </div>
    )
}