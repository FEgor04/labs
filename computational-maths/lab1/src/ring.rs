use std::ops::{Add, Mul, Neg};

pub trait Zero: Add<Self, Output = Self> + Sized {
    fn zero() -> Self;
    fn is_zero(&self) -> bool;
    fn set_zero(&mut self);
}

pub trait One: Mul<Self, Output = Self> + Sized {
    fn one() -> Self;
    fn set_one(&mut self);
    fn is_one(&self) -> bool;
}

pub trait RingElement: Zero + One + Neg<Output = Self> {}

macro_rules! impl_one_trait {
    ($t:ty, $v:expr) => {
        impl One for $t {
            #[inline]
            fn one() -> $t {
                $v
            }
            fn is_one(&self) -> bool {
                *self == $v
            }
            fn set_one(&mut self) {
                *self = $v
            }
        }
    };
}

macro_rules! impl_zero_trait {
    ($t:ty, $v:expr) => {
        impl Zero for $t {
            #[inline]
            fn zero() -> $t {
                $v
            }
            fn is_zero(&self) -> bool {
                *self == $v
            }
            fn set_zero(&mut self) {
                *self = $v
            }
        }
    };
}

impl_one_trait!(f64, 1.0);
impl_one_trait!(i32, 1);

impl_zero_trait!(f64, 0.0);
impl_zero_trait!(i32, 0);

impl RingElement for f64 {}
impl RingElement for i32 {}
