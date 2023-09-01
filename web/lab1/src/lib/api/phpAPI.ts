import {HitAPI, HitAPIError, HitAPIResponse} from "./index.ts";
import {Point} from "../../model.ts";
import {Err, Ok, Result} from "../result.ts";

export class PhpAPI implements HitAPI {
    private baseUrl = "."

        async hit(point: Point, r: number): Promise<Result<HitAPIResponse, HitAPIError>> {
        try {
            const response = await fetch(`${this.baseUrl}/action.php?x=${point.x}&y=${point.y}&r=${r}`)
            if(!response.ok) {
                return Err(HitAPIError.NetworkError)
            }
            if(response.status >= 500) {
                return Err(HitAPIError.InternalServerError)
            }
            if(response.status>= 400) {
                return Err(HitAPIError.ClientError)
            }

            const json: HitAPIResponse = await response.json()
            console.log(json)
            // @ts-ignore
            json.currentTime *= 1000;
            return Ok(json)
        }
        catch(e) {
            return Err(HitAPIError.UnknownError)
        }
    }

}
