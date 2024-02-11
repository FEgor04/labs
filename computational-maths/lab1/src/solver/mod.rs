use crate::{matrix::{SquareMatrix, Vector}, ring::RingElement};

/// Solver of system of linear equations with `N` unknown variables and `N`
/// equations.
/// Returns a solution vector `x` so that 
/// `a * x = b`
trait SLESovler<T: RingElement + std::marker::Copy, const N: usize> {
    fn solve(a: &SquareMatrix<T, N>, b: &Vector<T, N>) -> Vector<T, N>;
}
