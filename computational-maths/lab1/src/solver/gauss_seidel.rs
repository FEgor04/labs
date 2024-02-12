use crate::ring::RingElement;

use super::SLESolver;

pub struct GaussSeidelSolver {}

impl <T: RingElement + std::cmp::Ord + std::cmp::PartialOrd + Copy + std::iter::Sum> SLESolver<T> for GaussSeidelSolver {
    fn solve_sle(sle: &mut crate::sle::SLE<T>) -> crate::matrix::DMatrix<T> {
        todo!()
    }
}
