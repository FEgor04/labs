mod gauss_seidel;

use crate::{
    matrix::{SquareMatrix, Vector},
    ring::RingElement,
    sle::SLE,
};

/// Solver of system of linear equations with `N` unknown variables and `N`
/// equations.
/// Returns a solution vector `x` so that
/// `a * x = b`
trait SLESovler<T: RingElement + std::marker::Copy, const N: usize> {
    fn solve(sle: SLE<T, N>) -> Vector<T, N>;
}
