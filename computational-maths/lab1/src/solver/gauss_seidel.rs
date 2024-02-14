use crate::{field::FieldElement, matrix::DMatrix, sle::SLE};

use super::SLESolver;

pub struct GaussSeidelSolver<T: FieldElement> {
    precision: T,
}

impl<T: FieldElement + std::cmp::PartialOrd + Copy + std::iter::Sum> SLESolver<T>
    for GaussSeidelSolver<T>
{
    fn solve_sle(&self, sle: &mut SLE<T>) -> DMatrix<T> {
        let mut current_approximation = GaussSeidelSolver::compute_d_vector(sle);
        let mut previous_approximation: DMatrix<T> = DMatrix::new_zeroed(1, sle.n);
        while self.should_iterate_next(&previous_approximation, &current_approximation) {
            previous_approximation = current_approximation.clone();
            for i in 0..sle.n {
                let mut value = sle.b.get(0, i) / sle.a.get(i, i);
                let mut current_step_sum = T::zero();
                for j in 0..i-1 {
                    current_step_sum = current_step_sum + sle.a.get(i, j) / sle.a.get(i, i) * current_approximation.get(0, j);
                }
                let mut previous_step_sum = T::zero();
                for j in i+1..sle.n {
                    previous_step_sum = previous_step_sum + sle.a.get(i, j) / sle.a.get(i, i) * previous_approximation.get(0, j);
                }

                value = value + (-current_step_sum) + (-previous_step_sum);
                current_approximation.set(0, i, value);
            }
        }
        current_approximation
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


impl<T: FieldElement + Copy + std::cmp::PartialOrd> GaussSeidelSolver<T> {
    fn should_iterate_next(&self, previous_approximation: &DMatrix<T>, current_approximation: &DMatrix<T>) -> bool {
        let abs = |x: T| -> T {
            if x > T::zero() {
                x
            }
            else {
                - x
            }
        };
        for i in 0..previous_approximation.get_nrows() {
            if abs(previous_approximation.get(0, i) + (-current_approximation.get(0, i))) > self.precision {
                return false;
            }
        }
        return true;
    }
}


#[cfg(test)]
mod tests {
    use crate::{matrix::DMatrix, sle::SLE, solver::SLESolver};

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

    #[test]
    fn test_gauss_seidel() {
        let precision = 0.000001;
        let a = DMatrix::new_from_array([[10.0, 1.0, 1.0], [2.0, 10.0, 1.0], [2.0, 2.0, 10.0]]);
        let b = DMatrix::new_from_array([[12.0], [13.0], [14.0]]);
        let mut sle = SLE { a: a.clone(), b: b.clone(), n: 3 };
        let solver = GaussSeidelSolver { precision };
        let x = solver.solve_sle(&mut sle);
        let b_actual = a * x.clone();
        for i in 0..3 {
            let expected = b.get(0, i);
            let actual = b_actual.get(0,i);
            println!("{} {}", expected, actual);
            // assert!( (expected - actual).abs() <= precision );
            println!("x{} = {}", i, x.get(0, i));
        }
    }
}

