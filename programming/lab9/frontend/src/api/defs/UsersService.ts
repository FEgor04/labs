export interface UsersService {
    getUsers(pageNumber: number, pageSize: number): Promise<GetUsersResponse>
    grantPermissions(userId: number, canEdit: boolean, canDelete: boolean): Promise<void>
}

export type User = {
    id: number
    username: String,
    canYouDelete: boolean,
    canYouEdit: boolean
    canHeEdit: boolean,
    canHeDelete: boolean,
}
export type GetUsersResponse = {
    content: User[]
    totalPages: number
    totalElements: number
}