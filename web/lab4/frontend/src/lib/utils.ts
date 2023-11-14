import { type ClassValue, clsx } from 'clsx';
import { twMerge } from 'tailwind-merge';

export function cn(...inputs: ClassValue[]) {
    return twMerge(clsx(inputs));
}

async function sleep(ms: number) {
    return new Promise<void>((resolve) => setTimeout(resolve, ms));
}

export async function minDelay<T>(promise: Promise<T>, ms: number): Promise<T> {
    let [p] = await Promise.all([promise, sleep(ms)]);

    return p;
}
