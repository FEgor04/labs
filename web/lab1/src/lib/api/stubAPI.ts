import { HitAPI, HitAPIError, HitAPIResponse } from "./index.ts";
import { Point } from "../../model.ts";
import { Ok, Result } from "../result.ts";

export class StubAPI implements HitAPI {
  async hit(
    // @ts-ignore
    point: Point,
    // @ts-ignore
    r: number,
  ): Promise<Result<HitAPIResponse, HitAPIError>> {
    return Ok({
      currentTime: new Date(),
      executionTime: 10000,
      hit: true,
    });
  }
}
