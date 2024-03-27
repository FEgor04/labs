FIGURES_DIR := figures
FIGURES_SRC_DIR := src/python

AUX_DIR := aux

DEP_FILE := .deps.d

LATEXMK_FLAGS := -pdf -pdflatex="pdflatex -interaction=nonstopmode" \
								 -use-make -auxdir=$(AUX_DIR) 											\
								 -M -MF $(DEP_FILE)

.PHONY: clean format pvc spell

CONFORMAL_FIGURES := $(wildcard figures/conformal_*.pdf)

all: main.pdf

main.pdf: main.tex $(AUX_DIR) $(FIGURES_DIR)
	latexmk $(LATEXMK_FLAGS) $<

pvc: main.tex
	latexmk $(LATEXMK_FLAGS) -pvc $<

figures/%.pdf: src/python/%.py src/python/plot_utils.py venv/dependencies_touchfile venv/bin/activate
	source venv/bin/activate; python3 $< --save $@

$(CONFORMAL_FIGURES): src/python/conformal_utils.py

clean:
	rm -rf $(FIGURES_DIR) $(AUX_DIR) $(DEP_FILE) main.pdf **/*.bak* src/**/*.bak* *.bbl *.nav *.run.xml *.snm **/*.log src/*/*.log
	latexmk -C main.tex 

format:
	latexindent src/**/*.tex -w

spell:
	hunspell -d ru_RU,en_US -l -t src/**/*.tex src/*.tex main.tex

venv/bin/activate:
	python3 -m venv $@

venv/dependencies_touchfile: venv/bin/activate
	source $<; pip install numpy matplotlib
	touch $@

$(FIGURES_DIR) $(AUX_DIR):
	mkdir $@

$(DEP_FILE):
	touch $@

-include $(DEP_FILE)
