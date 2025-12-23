const GATEWAY_URL = import.meta.env.VITE_GATEWAY;

const COURSE_URL = `${GATEWAY_URL}/api/course`;
const AUTH_URL = `${GATEWAY_URL}/api/auth`;
const USER_URL = `${GATEWAY_URL}/api/user`;
export { GATEWAY_URL, COURSE_URL, AUTH_URL, USER_URL };
