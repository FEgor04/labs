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
