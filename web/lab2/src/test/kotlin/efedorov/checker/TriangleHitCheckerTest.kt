package efedorov.checker

import efedorov.model.Point
import kotlin.test.Test

class TriangleHitCheckerTest {
    val checker = TriangleHitChecker()

    @Test
    fun testShouldBeOk() {
        assert(checker.test(Point(2.0, 1.0, 3.0)))
        assert(checker.test(Point(1.0, 1.0, 3.0)))
        assert(checker.test(Point(0.0, 1.0, 3.0)))
        assert(checker.test(Point(0.0, 3.0, 3.0)))
    }

    @Test
    fun testMiss() {
        assert(!checker.test(Point(4.0, 1.0, 3.0)))
        assert(!checker.test(Point(3.0, 1.0, 3.0)))
        assert(!checker.test(Point(3.0, 2.0, 3.0)))
        assert(!checker.test(Point(-1.0, 2.0, 3.0)))
    }
}