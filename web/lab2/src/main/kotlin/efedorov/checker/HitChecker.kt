package efedorov.checker

import efedorov.model.Point
import java.util.function.Predicate

interface HitChecker: Predicate<Point> {
}