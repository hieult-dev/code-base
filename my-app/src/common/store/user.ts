import { create } from 'zustand';
import type { User } from '../../common/model/User';
import { persist } from 'zustand/middleware';

interface AuthState {
    user: User | null;
    authentication: string;
    refreshToken: string;
    expired?: Date;
    userRole: string;

    setUser: (user: User) => void;
    setUserRole: (userRole: string) => void;
    setAuthentication: (token: string) => void;
    setRefreshToken: (token: string) => void;
    resetUserStore: () => void;
}

const expiredAfter = 12 * 60 * 60 * 1000;
const getExpiredDate = () => {
    const date = new Date();
    date.setTime(date.getTime() + expiredAfter);
    return date;
};
export const useUserStore = create(
    persist<AuthState>(
        (set) => ({
            user: null,
            authentication: '',
            userRole: '',
            refreshToken: '',
            expired: undefined,

            setUser: (user) =>
                set({
                    user,
                    expired: getExpiredDate(),
                }),

            setAuthentication: (token) =>
                set({
                    authentication: token,
                    expired: getExpiredDate(),
                }),

            setRefreshToken: (token) =>
                set({
                    refreshToken: token,
                    expired: getExpiredDate(),
                }),

            resetUserStore: () =>
                set({
                    user: null,
                    authentication: '',
                    userRole: '',
                    refreshToken: '',
                    expired: undefined,
                }),

            setUserRole: (userRole) =>
                set({
                    userRole,
                    expired: getExpiredDate(),
                }),
        }),
        {
            name: 'user-store',
        }
    )
);
