pub mod gauss_seidel;

use crate::{matrix::DMatrix, ring::RingElement, sle::SLE};

trait SLESolver<T: RingElement> {
    /// Tries to solve given SLE.
    /// It can mutate `sle` if needed.
    ///
    /// Returns column-vector of `x`
    fn solve_sle(sle: &mut SLE<T>) -> DMatrix<T>;
}
