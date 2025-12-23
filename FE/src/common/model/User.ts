export class User {
    id?: string;
    fullName?: string;
    email?: string;
    constructor(
        id?: string,
        email?: string,
        fullName?: string,
    ) {
        this.id = id;
        this.email = email;
        this.fullName = fullName;

    }
}
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