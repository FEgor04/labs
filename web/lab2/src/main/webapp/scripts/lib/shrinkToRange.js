export default function shrinkToRange(number, low, high) {
    return Math.min(high, Math.max(number, low))
}
