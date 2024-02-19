use std::str::FromStr;
use std::fmt::Debug;

use crate::matrix::DMatrix;

pub trait Reader<T> { 
    fn get_n(&self) -> usize;
    fn get_precision(&self) -> T;
    fn get_a(&self) -> DMatrix<T>;
    fn get_b(&self) -> DMatrix<T>;
}

pub struct StdinReader<T: Copy> {
    n: usize,
    precision: T,
    a: DMatrix<T>,
    b: DMatrix<T>
}

impl<T: Copy> Reader<T> for StdinReader<T> {
    fn get_n(&self) -> usize {
        self.n
    }

    fn get_precision(&self) -> T {
        self.precision
    }

    fn get_a(&self) -> DMatrix<T> {
        self.a.clone()
    }

    fn get_b(&self) -> DMatrix<T> {
        self.b.clone()
    }
}

fn read_string() -> String {
    let mut input = String::new();
    std::io::stdin()
        .read_line(&mut input)
        .expect("can not read user input");
    input
}

fn read_matrix<T: Copy + FromStr>(n_rows: usize) -> DMatrix<T> where <T as FromStr>::Err: Debug {
    let mut rows = Vec::<Vec<T>>::new();
    for _ in 0..n_rows {
        let row: Vec<T> = read_string().trim().split(" ").map(|x| x.parse::<T>().unwrap()).collect();
        rows.push(row);
    }
    DMatrix::new(rows)
}


impl<T: Copy + FromStr> StdinReader<T> {
    pub fn new() -> Self where <T as FromStr>::Err: Debug {
        println!("Введите количество уравнений");
        let n = read_string().trim().parse::<usize>().unwrap();
        println!("Введите точность вычислений");
        let precision = read_string().trim().parse::<T>().unwrap();
        println!("Введите матрицу A");
        let a = read_matrix(n);
        println!("Введите вектор B");
        let b = read_matrix(n);
        Self {
            n,
            precision,
            a,
            b
        }
    }
}


