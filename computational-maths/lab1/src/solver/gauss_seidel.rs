use crate::{
    matrix::{SquareMatrix, Vector},
    ring,
    sle::SLE,
};

use super::SLESovler;

struct GaussSeiledSLESolver {}

impl<T: ring::RingElement + std::cmp::PartialOrd + std::marker::Copy, const N: usize>
    SLESovler<T, N> for GaussSeiledSLESolver
{
    fn solve(sle: SLE<T, N>) -> Vector<T, N> {
        todo!()
    }
}
