import { AUTH_URL } from "../../../config/api";
import { useUserStore } from "../../../store/user";
import { REFRESH_TOKEN_URL } from "./baseApi";
import baseApi from "./baseApi";
import type { UserLoginResponse } from "../../../model/User";

type TokenRefresh = {
    token: string,
    refreshToken: string,
    role: string
};

function login(email: string, password: string, otpCode: string | undefined) {
    return baseApi.post<UserLoginResponse>(`${AUTH_URL}/authenticate`, {
        email,
        password,
        otpCode
    });
}

function logout() {
    const refreshToken = useUserStore.getState().refreshToken;

    if (!refreshToken) {
        return Promise.resolve();
    }

    return baseApi.post<void>(`${AUTH_URL}/logout`, {
        refreshToken
    });
}

let refreshPromise: Promise<void> | null = null;
async function refreshToken(): Promise<void> {
    const authStore = useUserStore.getState();

    if (!authStore.refreshToken) {
        return Promise.reject('No refresh token');
    }

    if (refreshPromise) {
        return refreshPromise;
    }

    refreshPromise = (async () => {
        try {
            const rs = await baseApi.post<TokenRefresh>(
                REFRESH_TOKEN_URL,
                { refreshToken: authStore.refreshToken }
            );

            if (rs) {
                authStore.setAuthentication(`Bearer ${rs.token}`);
                authStore.setRefreshToken(rs.refreshToken);
                authStore.setUserRole(rs.role);
            }
        } finally {
            refreshPromise = null;
        }
    })();

    return refreshPromise;
}

export { login, logout, refreshToken };
