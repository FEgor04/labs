import {User, UsersService} from "../api/defs/UsersService.ts";
import {makeAutoObservable} from "mobx";
import {notification} from "antd";
import {AxiosUsersService} from "../api/implementation/axios/AxiosUsersService.ts";
import {use} from "i18next";

export class UsersStore {
    currentPage: number = 1
    pageSize: number = 10
    isLoading: boolean = true
    totalPages: number | null = null
    totalElements: number | null = null
    private usersService: UsersService = new AxiosUsersService()
    users: User[]

    constructor() {
        makeAutoObservable(this)
    }

    updateData() {
        this.setLoading(true)
        this.usersService.getUsers(this.currentPage - 1, this.pageSize).then((data) => {
            this.setUsers(data.content)
            this.setTotalElements(data.totalElements)
            this.setTotalPages(data.totalPages)
        }).catch((e) => {
            notification.open({
                message: "Error!",
                description: e,
                type: "error"
            })
        }).finally(() => {
            this.setLoading(false)
        })
    }

    grantPermissions(userId: number, canEdit: boolean, canDelete: boolean) {
        this.usersService.grantPermissions(userId, canEdit, canDelete).then((_) => {
        }).catch((e) => {
            notification.open({
                type: "error",
                message: "Error!",
                description: e
            })
        })
    }

    setLoading(loading: boolean) {
        this.isLoading = loading
    }

    setPageSize(pageSize: number) {
        this.pageSize = pageSize
    }

    setCurrentPage(page: number) {
        this.currentPage = page
    }

    setUsers(users: User[]) {
        this.users = users
    }

    setTotalPages(total: number) {
        this.totalPages = total
    }

    setTotalElements(total: number) {
        this.totalElements = total
    }
}