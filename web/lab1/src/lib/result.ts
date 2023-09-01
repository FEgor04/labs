type Ok<T> = { ok: true, value: T };

export type Err<E> = { ok: false, error: E }

export type Result<T, E> = Ok<T> | Err<E>

export const Ok = <T>(value: T): Ok<T> => {
    return {ok: true, value}
};

export const Err = <E>(error: E): Err<E> => {
    return {ok: false, error}
}

export function parseIntResult(s: string, radix: number = 10): Result<number, string> {
    let number = parseInt(s, radix)
    if (isNaN(number) || !isNumeric(s)) {
        return Err("number is not a number")
    }
    return Ok(number)
}

function isNumeric(s: string) {
    return /^(\d|\+|-)+$/.test(s);
}