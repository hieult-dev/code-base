import { COURSE_URL } from "../../../common/config/api";
import baseApi from "../../../common/auth/api/rest/baseApi";
import ICourse from '@/apps/course/model/Course';

function fetchCoursesForAdmin() {
    return baseApi.get<ICourse[]>(`${COURSE_URL}/getAllCourseForAdmin`);
}

export { fetchCoursesForAdmin };
