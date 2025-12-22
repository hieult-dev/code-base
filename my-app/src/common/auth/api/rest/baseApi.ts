import type { AxiosRequestConfig, AxiosResponse } from 'axios';
import axios from 'axios';
import { GATEWAY_URL } from '../../../config/api';
import { useUserStore } from '../../../store/user';
import { resetStoreAndRedirectToLogin } from '../../../service/reset_store';
import { refreshToken } from './authApi';

export const REFRESH_TOKEN_URL = `${GATEWAY_URL}/auth/refreshToken`;
const LOGIN_URL = `${GATEWAY_URL}/api/auth/authenticate`;

export function initialClient(
    hasFile: boolean,
    isDownload = false,
    isSync = false
) {
    const axiosInstance = axios.create({
        headers: {
            Accept: '*/*',
            'Content-Type': hasFile
                ? 'multipart/form-data'
                : 'application/json',
        },
        responseType: isDownload ? 'blob' : 'json',
        timeout: isSync ? 1800000 : 120000,
    });

    axiosInstance.interceptors.request.use(
        (config) => {
            const { authentication } = useUserStore.getState();
            if (authentication) {
                config.headers = config.headers || {};
                config.headers['Authorization'] = authentication;
            }
            return config;
        },
        (error) => Promise.reject(error)
    );

    // ===== Response interceptor =====
    axiosInstance.interceptors.response.use(
        (res) => res,
        async (err) => {
            const originalConfig = err.config;

            if (originalConfig?.url !== LOGIN_URL && err.response) {
                // Refresh token expired
                if (
                    err.response.status === 401 &&
                    originalConfig.url === REFRESH_TOKEN_URL
                ) {
                    resetStoreAndRedirectToLogin();
                    return Promise.reject(err);
                }

                // Access token expired
                if (err.response.status === 403 && !originalConfig._retry) {
                    originalConfig._retry = true;
                    try {
                        await refreshToken();
                        return axiosInstance(originalConfig);
                    } catch (_error) {
                        resetStoreAndRedirectToLogin();
                        return Promise.reject(_error);
                    }
                }
            }

            return Promise.reject(err);
        }
    );

    return axiosInstance;
}

export async function request<T>(
    method: 'get' | 'post' | 'put' | 'patch' | 'delete',
    path: string,
    data?: object,
    hasFile = false,
    isDownload = false,
    isSync = false,
    config?: AxiosRequestConfig
): Promise<T> {
    const client = initialClient(hasFile, isDownload, isSync);

    try {
        let response: AxiosResponse<T>;

        switch (method) {
            case 'get':
                response = await client.get<T>(path, config);
                break;

            case 'post':
                response = await client.post<T>(path, data ?? {}, config);
                break;

            case 'put':
                response = await client.put<T>(path, data ?? {}, config);
                break;

            case 'patch':
                response = await client.patch<T>(path, data ?? {}, config);
                break;

            case 'delete':
                response = await client.delete<T>(path, config);
                break;

            default:
                response = await client.get<T>(path, config);
                break;
        }

        return response.data;
    } catch (err: any) {
        throw err?.response?.data ?? err;
    }
}


function post<T>(path: string, data: object | undefined, config?: AxiosRequestConfig) {
    return request<T>('post', path, data, false, false, false, config);
}

export default {
    post,
    request
};