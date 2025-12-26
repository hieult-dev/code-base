import { COURSE_URL } from "../../../common/config/api";
import baseApi from "../../../common/auth/api/rest/baseApi";
import type ICourse from "../model/Course";

function fetchCourses() {
    return baseApi.get<ICourse[]>(`${COURSE_URL}/getAllCoursePublic`);
}

function getCourseDetailById(courseId: number) {
    return baseApi.get<ICourse>(`${COURSE_URL}/getCourseById/${courseId}`);
}

export { fetchCourses, getCourseDetailById };