import {Point} from "../../model.ts";
import {Result} from "../result.ts";

export interface HitAPI {
    /**
     * Проверяет попадаение пользователя в точку.
    */
    hit(point: Point, r: number): Promise<Result<HitAPIResponse, HitAPIError>>
}

export type HitAPIResponse = {
    hit: boolean,
    currentTime: Date,
    executionTime: number,
}

export enum HitAPIError {
    NetworkError = "Ошибка сети.",
    InternalServerError = "Внутренняя ошибка сервера",
    ClientError = "Клиентская ошибка",
    UnknownError = "Неизвестная ошибка(( Простите((",
}
