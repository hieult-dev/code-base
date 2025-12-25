import '../style/CourseCard.css';
export default function CourseCard({ course }) {
    return (
        <div className="course-card">
            <h3 className="course-title">{course.title}</h3>

            <p className="course-description">
                {course.description}
            </p>

            <div className="course-footer">
                <span className="course-price">
                    ${course.price}
                </span>

                <span className="course-date">
                    {new Date(course.createdAt).toLocaleDateString()}
                </span>
            </div>
        </div>
    )
}