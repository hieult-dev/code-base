import { useUserStore } from '../store/user';

export function resetStoreAndRedirectToLogin() {
    resetStore();
    window.location.replace('/login');
}
export function resetStore() {
    useUserStore().resetUserStore();
}