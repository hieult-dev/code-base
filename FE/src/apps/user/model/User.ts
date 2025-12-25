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
    createdAt?: string;
    role: UserRole;
}

export enum UserRole {
    ADMIN = 'ADMIN',
    INSTRUCTOR = 'INSTRUCTOR',
    STUDENT = 'STUDENT',
}
