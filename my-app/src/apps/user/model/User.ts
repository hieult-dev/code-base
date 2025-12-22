export interface UserLoginResponse {
    user: UserInterface;
    accessToken: string;
    refreshToken: string;
    tokenType: string;
}

export interface UserInterface {
    id?: string;
    fullName: string;
    email: string;
}