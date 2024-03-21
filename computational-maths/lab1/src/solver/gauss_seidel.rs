use crate::{field::FieldElement, matrix::DMatrix, print_matrix, sle::SLE};

use super::{SLESolution, SLESolver};

pub struct GaussSeidelSolver<T: FieldElement> {
    pub precision: T,
}

pub const MAX_ITERATIONS_COUNT: usize = 1024;

impl<T: FieldElement + std::cmp::PartialOrd + Copy + std::iter::Sum> SLESolver<T>
    for GaussSeidelSolver<T>
{
    fn solve_sle(&self, sle: &SLE<T>) -> SLESolution<T> {
        let mut iterations_count: usize = 0;
        let c_matrix = GaussSeidelSolver::compute_c_matrix(sle);
        let d_vector = GaussSeidelSolver::compute_d_vector(sle);
        let mut current_approximation = d_vector.clone();
        let mut previous_approximation: DMatrix<T> = DMatrix::new_zeroed(1, sle.n);
        let mut error_vector =
            (current_approximation.clone() + (-previous_approximation.clone())).abs();
        while self.should_iterate_next(&error_vector) && iterations_count < MAX_ITERATIONS_COUNT {
            previous_approximation = current_approximation.clone();
            for i in 0..sle.n {
                let mut value = d_vector.get(i, 0);
                for j in 0..i {
                    value = value + c_matrix.get(i, j) * current_approximation.get(j, 0);
                }
                for j in i..sle.n {
                    value = value + c_matrix.get(i, j) * previous_approximation.get(j, 0);
                }
                current_approximation.set(i, 0, value);
            }
            iterations_count += 1;
            error_vector =
                (current_approximation.clone() + (-previous_approximation.clone())).abs();
        }
        SLESolution {
            solution: current_approximation,
            iterations_count,
            error_vector,
        }
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
    fn should_iterate_next(&self, error_vector: &DMatrix<T>) -> bool {
        for i in 0..error_vector.get_nrows() {
            if error_vector.get(i, 0) > self.precision {
                return true;
            }
        }
        return false;
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
        let mut sle = SLE {
            a: a.clone(),
            b: b.clone(),
            n: 3,
        };
        let solver = GaussSeidelSolver { precision };
        let solution = solver.solve_sle(&mut sle);
        let x = solution.solution;
        let b_actual = a * x.clone();
        println!("X: {} * {}", x.get_nrows(), x.get_ncols());
        println!("B: {} * {}", b.get_nrows(), b.get_ncols());
        println!(
            "B_ACTUAL: {} * {}",
            b_actual.get_nrows(),
            b_actual.get_ncols()
        );
        for i in 0..3 {
            let expected = b.get(i, 0);
            let actual = b_actual.get(i, 0);
            println!("{} {}", expected, actual);
            println!("x{} = {}", i, x.get(i, 0));
            assert!((expected - actual) < precision);
        }
    }
}
