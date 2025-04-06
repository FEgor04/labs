FIGURES := autocorrelation_plot.pdf exponential_fit.pdf histogram_plot.pdf sequence_plot.pdf
DEP_FILE := .deps.d
LATEXMK_FLAGS := -pdf --shell-escape -pdflatex="pdflatex -interaction=nonstopmode" \
								 -use-make \
								 -M -MF $(DEP_FILE)

.PHONY: main.pdf clean format pvc spell

all: main.pdf

main.pdf: main.tex
	latexmk $(LATEXMK_FLAGS) $<

pvc: main.tex
	latexmk $(LATEXMK_FLAGS) -pvc $<

clean:
	rm -rf $(DEP_FILE) main.pdf **/*.bak* src/**/*.bak* *.bbl *.nav *.run.xml *.snm **/*.log src/*/*.log __pycache__ _minted-main
	latexmk -C main.tex 

format:
	latexindent src/*.tex -w

spell:
	hunspell -d ru_RU,en_US -l -t src/**/*.tex src/*.tex main.tex

figures:
	mkdir -p figures

$(FIGURES): lab1/__init__.py
	poetry run python $<
