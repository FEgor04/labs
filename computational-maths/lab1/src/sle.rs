use crate::{
    matrix::{SquareMatrix, Vector},
    ring::RingElement,
};

/// System of linear algebraic equations.
/// `T` - type of data. Should be an element of ring
/// `N` - number of equations = number of unknowns
pub struct SLE<T: RingElement + std::cmp::PartialOrd + std::marker::Copy, const N: usize> {
    a: SquareMatrix<T, N>,
    b: Vector<T, N>,
}
