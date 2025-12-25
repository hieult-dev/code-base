import { useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react'
import { fetchCourses } from '../../apps/course/api/course-api-rest';
import '../assets/Home.css'
import CourseCard from '../../apps/course/components/CourseCard';
export default function Home() {
    const [courses, setCourses] = useState([])
    const [loading, setLoading] = useState(true)
    const [error, setError] = useState(null)

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

    if (loading) {
        return <p>Loading courses...</p>
    }

    if (error) {
        return <p style={{ color: 'red' }}>{error}</p>
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