use crate::{field::FieldElement, matrix::DMatrix, ring::RingElement, sle::SLE};

use super::SLESolver;

pub struct GaussSeidelSolver<T: FieldElement> {
    current_approximation: DMatrix<T>,
    previous_approximation: DMatrix<T>,
}

impl<T: FieldElement + std::cmp::Ord + std::cmp::PartialOrd + Copy + std::iter::Sum> SLESolver<T>
    for GaussSeidelSolver<T>
{
    fn solve_sle(sle: &mut crate::sle::SLE<T>) -> crate::matrix::DMatrix<T> {
        sle.transform_to_diagonally_dominant();

        todo!()
    }
}

impl<T: FieldElement + Copy> GaussSeidelSolver<T> {
    /// Computes a `c` matrix.
    /// ```
    /// c_{i,j} = {
    ///     0                       if i = j
    ///     - (a_{i,j}) / (a_{i,i}) otherwise
    /// }
    /// `
    fn compute_c_matrix(sle: &SLE<T>) -> DMatrix<T> {
        let n = sle.get_n();
        let mut c = DMatrix::new_zeroed(n, n);
        for i in 0..n {
            for j in 0..n {
                let value = if i == j {
                    T::zero()
                } else {
                    -sle.a.get(i, j) / sle.a.get(i, i)
                };
                c.set(i, j, value);
            }
        }
        c
    }

    fn compute_d_vector(sle: &SLE<T>) -> DMatrix<T> {
        let n = sle.n;
        let mut d = DMatrix::new_zeroed(1, n);
        for i in 0..n {
            let b_value = sle.b.get(i, 0);
            let a_value = sle.a.get(i, i);
            d.set(i, 0, b_value / a_value);
        }
        d
    }
}

#[cfg(test)]
mod tests {
    use crate::{matrix::DMatrix, sle::SLE};

    use super::GaussSeidelSolver;

    #[test]
    fn compute_c_d() {
        let a = DMatrix::new_from_array([[10.0, 1.0, 1.0], [2.0, 10.0, 1.0], [2.0, 2.0, 10.0]]);
        let b = DMatrix::new_from_array([[12.0], [13.0], [14.0]]);
        let sle = SLE { a, b, n: 3 };
        let c = GaussSeidelSolver::compute_c_matrix(&sle);
        let d = GaussSeidelSolver::compute_d_vector(&sle);
        let c_expected =
            DMatrix::new_from_array([[0.0, -0.1, -0.1], [-0.2, 0.0, -0.1], [-0.2, -0.2, 0.0]]);
        let d_expected = DMatrix::new_from_array([[1.2], [1.3], [1.4]]);
        assert_eq!(c_expected, c);
        assert_eq!(d_expected, d);
    }
}
