import { HistoryEntry } from "../model.ts";

export interface HistoryManager {
  /**
   * Добавляет новую запись в историю
   */
  push(entry: HistoryEntry): void;

  /**
   * Возвращает все записи в истории
   */
  get(): HistoryEntry[];

  /**
   * Возвращает последнее вхождение в историю
   */
  getLastEntry(): HistoryEntry | undefined;

  /**
   * Очищает историю
   */
  cleanHistory(): void;
}
