# Makefile for LLVM-J

LLVM_VERSION=3.8
JNAERATOR_VERSION=0.12-SNAPSHOT-20130727

# Path to LLVM such that headers for the C bindings exist
# in $LLVM_HOME/include/llvm-c/
export LLVM_C=/usr/include/llvm-c

JAVA=java

.PHONY: help clean llvm

.DEFAULT_GOAL := help

help:
	@echo "Use \`make <target> [LLVM_C=<path>]' where <path> is the"
	@echo "location of the LLVM headers for th C-bindings."
	@echo "    help     to display this help message"
	@echo "    clean    to remove existing bindings"
	@echo "    llvm     to generate Java bindings to LLVM using JNAerator"

clean:
	-rm src/main/java/org/llvm/binding/LLVMLibrary.java
	-rm src/main/java/org/llvm/binding/LLVMMCJITCompilerOptions.java

llvm: clean src/main/java/org/llvm/binding/LLVMLibrary.java

src/main/java/org/llvm/binding/LLVMLibrary.java: jnaerator.jar config.jnaerator
	$(JAVA) -jar $<
	sed -i 's/@Library("LLVM")/@Library("LLVM-$(LLVM_VERSION)")/' $@

jnaerator.jar:
	wget http://jnaerator.googlecode.com/files/jnaerator-$(JNAERATOR_VERSION).jar -O $@
