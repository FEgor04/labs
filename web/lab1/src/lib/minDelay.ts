async function sleep(ms: number) {
  return new Promise<void>((resolve) => setTimeout(resolve, ms));
}

export async function minDelay<T>(promise: Promise<T>, ms: number) {
  const [p] = await Promise.all([promise, sleep(ms)]);
  return p;
}
