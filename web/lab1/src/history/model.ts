import { Point } from "../model.ts";
import { HitAPIResponse } from "../lib/api";

export type HistoryEntry = {
  point: Point;
  r: number;
} & HitAPIResponse;

export type History = HistoryEntry[];
