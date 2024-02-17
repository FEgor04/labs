use std::io::BufRead;

use matrix::DMatrix;

use crate::{sle::SLE, solver::{gauss_seidel::GaussSeidelSolver, SLESolver}};

mod field;
pub mod matrix;
mod ring;
mod sle;
mod solver;

fn read_string() -> String {
    let mut input = String::new();
    std::io::stdin()
        .read_line(&mut input)
        .expect("can not read user input");
    input
}

fn read_matrix_f64(n_rows: usize) -> DMatrix<f64> {
    let mut rows = Vec::<Vec<f64>>::new();
    for _ in 0..n_rows {
        let row: Vec<f64> = read_string().trim().split(" ").map(|x| x.parse::<f64>().unwrap()).collect();
        rows.push(row);
    }
    DMatrix::new(rows)
}

fn print_matrix_f64(matrix: &DMatrix<f64>) {
    for i in 0..matrix.get_nrows() {
        for j in 0..matrix.get_ncols() {
            print!("{} ", matrix.get(i, j));
        }
        println!("");
    }
}

fn main() {
    println!("Введите количество уравнений");
    let n = read_string().trim().parse::<usize>().unwrap();
    println!("Введите точность вычислений");
    let precision = read_string().trim().parse::<f64>().unwrap();
    println!("Введите матрицу A");
    let a_matrix = read_matrix_f64(n);
    println!("Введите вектор B");
    let b_vec = read_matrix_f64(n);
    let mut sle = SLE {
        n, a: a_matrix, b: b_vec
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
            print_matrix_f64(&sle.a);
            println!("Вектор B");
            print_matrix_f64(&sle.b);
        }
    }

    let solver = GaussSeidelSolver { precision };
    let solution = solver.solve_sle(&sle);
    println!("Решение системы:");
    print_matrix_f64(&solution.solution);
    println!("Решение было достигнуто за {} итераций", solution.iterations_count);
    println!("Вектор ошибок:");
    print_matrix_f64(&solution.error_vector);
}
