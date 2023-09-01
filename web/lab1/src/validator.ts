import type {Result} from "./lib/result.ts";
import type {FormInput, RawFormInput} from "./model.ts";
import {Ok, Err, parseIntResult} from "./lib/result.ts";

/**
 * Проверяет корректность введенных данных
 * @throws InvalidDataError если одно из полей было введено некорректно
 **/
export function validateFormInput(input: RawFormInput): Result<FormInput, ValidationErrors> {
    const parsedX = parseIntResult(input.x);
    if (!parsedX.ok) {
        return Err(ValidationErrors.XIsNotANumber)
    }
    if (parsedX.value < -5 || parsedX.value > 3) {
        return Err(ValidationErrors.InvalidXValue)
    }

    const parsedY = parseIntResult(input.y);
    if (!parsedY.ok) {
        return Err(ValidationErrors.YIsNotANumber)
    }
    if (parsedY.value < -3 || parsedY.value > 3) {
        return Err(ValidationErrors.InvalidYValue)
    }

    const parsedR = parseIntResult(input.r);
    if (!parsedR.ok) {
        return Err(ValidationErrors.RIsNotANumber)
    }
    if (parsedR.value < 1 || parsedR.value > 5) {
        return Err(ValidationErrors.InvalidRValue)
    }

    return Ok({x: parsedX.value, y: parsedY.value, r: parsedR.value});
}


enum ValidationErrors {
    XIsNotANumber = "X не является числом.",
    InvalidXValue = "X должно быть от -5 до 3.",
    YIsNotANumber = "Y не является числом.",
    InvalidYValue = "Y должно быть от -3 до 3.",
    RIsNotANumber = "R не является числом.",
    InvalidRValue = "R должно быть от 1 до 5.",
}
