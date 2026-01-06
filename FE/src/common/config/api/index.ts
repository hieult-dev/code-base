const GATEWAY_URL = import.meta.env.VITE_GATEWAY;

const COURSE_URL = `${GATEWAY_URL}/api/course`;
const AUTH_URL = `${GATEWAY_URL}/api/auth`;
const USER_URL = `${GATEWAY_URL}/api/user`;
const CHAT_URL = `${GATEWAY_URL}/chat`;
const CHAT_WS_URL = import.meta.env.VITE_CHAT_WS;
export { GATEWAY_URL, COURSE_URL, AUTH_URL, USER_URL, CHAT_URL, CHAT_WS_URL };