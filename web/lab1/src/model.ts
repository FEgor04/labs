type FormInput = {
  x: number;
  y: number;
  r: number;
};

type RawFormInput = Record<keyof FormInput, any>;

export type { FormInput, RawFormInput };

export type Point = {
  x: number;
  y: number;
  hit: boolean | undefined
};
