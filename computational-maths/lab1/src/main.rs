use std::io::BufRead;

use matrix::DMatrix;

use crate::{reader::{Reader, StdinReader}, sle::SLE, solver::{gauss_seidel::GaussSeidelSolver, SLESolver}};

mod field;
pub mod matrix;
mod ring;
mod sle;
mod solver;
mod reader;

fn print_matrix<T: Copy + std::fmt::Display>(matrix: &DMatrix<T>) {
    for i in 0..matrix.get_nrows() {
        for j in 0..matrix.get_ncols() {
            print!("{} ", matrix.get(i, j));
        }
        println!("");
    }
}

fn main() {
    let reader = StdinReader::<f64>::new();
    let mut sle = SLE {
        n: reader.get_n(), a: reader.get_a(), b: reader.get_b()
    };

    if !sle.is_diagonally_dominant() {
        println!("Диагональное преобладание не выполняется.");
        sle.transform_to_diagonally_dominant();
        if !sle.is_diagonally_dominant() {
            println!("Невозможно привести систему к диагональному преобладанию. Вычисления невозможны.");
            return;
        }
        else {
            println!("Система была приведена к диагональному преобладанию. Матрица A:");
            print_matrix(&sle.a);
            println!("Вектор B");
            print_matrix(&sle.b);
        }
    }

    let solver = GaussSeidelSolver { precision: reader.get_precision() };
    let solution = solver.solve_sle(&sle);
    println!("Решение системы:");
    print_matrix(&solution.solution);
    println!("Решение было достигнуто за {} итераций", solution.iterations_count);
    println!("Вектор ошибок:");
    print_matrix(&solution.error_vector);
}
