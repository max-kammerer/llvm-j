package org.llvm;

import org.bridj.IntValuedEnum;
import org.bridj.Pointer;
import org.llvm.binding.LLVMLibrary.LLVMTypeKind;
import org.llvm.binding.LLVMLibrary.LLVMTypeRef;

import java.util.List;

import static org.llvm.binding.LLVMLibrary.*;

/**
 * Each value in the LLVM IR has a type, an LLVMTypeRef.
 */
public class TypeRef {

	private final LLVMTypeRef type;

	public LLVMTypeRef type() {
		return this.type;
	}

	TypeRef(LLVMTypeRef type) {
		this.type = type;
	}

	/**
	 * Obtain the enumerated type of a Type instance.
	 */
	public IntValuedEnum<LLVMTypeKind> getTypeKind() {
		return LLVMGetTypeKind(this.type);
	}

	/**
	 * Obtain the context to which this type instance is associated.
	 */
	public Context getTypeContext() {
		return new Context(LLVMGetTypeContext(this.type));
	}

	/**
	 * Obtain an integer type from a context with specified bit width.
	 */
	public static TypeRef int1TypeInContext(Context c) {
		return new TypeRef(LLVMInt1TypeInContext(c.context()));
	}

	public static TypeRef int8TypeInContext(Context c) {
		return new TypeRef(LLVMInt8TypeInContext(c.context()));
	}

	public static TypeRef int16TypeInContext(Context c) {
		return new TypeRef(LLVMInt16TypeInContext(c.context()));
	}

	public static TypeRef int32TypeInContext(Context c) {
		return new TypeRef(LLVMInt32TypeInContext(c.context()));
	}

	public static TypeRef int64TypeInContext(Context c) {
		return new TypeRef(LLVMInt64TypeInContext(c.context()));
	}

	public static TypeRef intTypeInContext(Context c, int NumBits) {
		return new TypeRef(LLVMIntTypeInContext(c.context(), NumBits));
	}

	/**
	 * Obtain an integer type from the global context with a specified bit<br>
	 * width.
	 */
	public static TypeRef int1Type() {
		return new TypeRef(LLVMInt1Type());
	}

	public static TypeRef int8Type() {
		return new TypeRef(LLVMInt8Type());
	}

	public static TypeRef int16Type() {
		return new TypeRef(LLVMInt16Type());
	}

	public static TypeRef int32Type() {
		return new TypeRef(LLVMInt32Type());
	}

	public static TypeRef int64Type() {
		return new TypeRef(LLVMInt64Type());
	}

	public static TypeRef intType(int NumBits) {
		return new TypeRef(LLVMIntType(NumBits));
	}

	public int getIntTypeWidth() {
		return LLVMGetIntTypeWidth(this.type);
	}

	/**
	 * Obtain a 32-bit floating point type from a context.
	 */
	public static TypeRef floatTypeInContext(Context c) {
		return new TypeRef(LLVMFloatTypeInContext(c.context()));
	}

	/**
	 * Obtain a 64-bit floating point type from a context.
	 */
	public static TypeRef doubleTypeInContext(Context c) {
		return new TypeRef(LLVMDoubleTypeInContext(c.context()));
	}

	/**
	 * Obtain a 80-bit floating point type (X87) from a context.
	 */
	public static TypeRef x86FP80TypeInContext(Context c) {
		return new TypeRef(LLVMX86FP80TypeInContext(c.context()));
	}

	/**
	 * Obtain a 128-bit floating point type (112-bit mantissa) from a<br>
	 * context.
	 */
	public static TypeRef FP128TypeInContext(Context c) {
		return new TypeRef(LLVMFP128TypeInContext(c.context()));
	}

	/**
	 * Obtain a 128-bit floating point type (two 64-bits) from a context.
	 */
	public static TypeRef PPCFP128TypeInContext(Context c) {
		return new TypeRef(LLVMPPCFP128TypeInContext(c.context()));
	}

	public static TypeRef floatType() {
		return new TypeRef(LLVMFloatType());
	}

	public static TypeRef doubleType() {
		return new TypeRef(LLVMDoubleType());
	}

	public static TypeRef x86FP80Type() {
		return new TypeRef(LLVMX86FP80Type());
	}

	public static TypeRef FP128Type() {
		return new TypeRef(LLVMFP128Type());
	}

	public static TypeRef PPCFP128Type() {
		return new TypeRef(LLVMPPCFP128Type());
	}

	/**
	 * Obtain a function type consisting of a specified signature.<br>
	 * The function is defined as a tuple of a return Type, a list of<br>
	 * parameter types, and whether the function is variadic.
	 */
	public static TypeRef functionType(TypeRef returnType,
			TypeRef... paramTypes) {
		return new TypeRef(LLVMFunctionType(returnType.type,
				internalize(paramTypes), paramTypes.length, 0));
	}

	public static TypeRef functionType(TypeRef returnType,
			List<TypeRef> paramTypes) {
		return new TypeRef(LLVMFunctionType(returnType.type,
				internalize(paramTypes), paramTypes.size(), 0));
	}

	public static TypeRef functionType(TypeRef returnType, boolean isVarArg,
			List<TypeRef> paramTypes) {
		return new TypeRef(LLVMFunctionType(returnType.type,
				internalize(paramTypes), paramTypes.size(), isVarArg ? 1 : 0));
	}

	/**
	 * Returns whether a function type is variadic.
	 */
	public boolean isFunctionVarArg() {
		return LLVMIsFunctionVarArg(this.type) != 0;
	}

	/**
	 * Obtain the Type this function Type returns.
	 */
	public TypeRef getReturnType() {
		return new TypeRef(LLVMGetReturnType(this.type));
	}

	/**
	 * Obtain the number of parameters this function accepts.
	 */
	public int countParamTypes() {
		return LLVMCountParamTypes(this.type);
	}

	/**
	 * Obtain the types of a function's parameters.<br>
	 * The Dest parameter should point to a pre-allocated array of<br>
	 * LLVMTypeRef at least LLVMCountParamTypes() large. On return, the<br>
	 * first LLVMCountParamTypes() entries in the array will be populated<br>
	 * with LLVMTypeRef instances.<br>
	 * 
	 * @param dest
	 *        Memory address of an array to be filled with result.
	 */
	// TODO Pointer
	public void getParamTypes(Pointer<LLVMTypeRef> dest) {
		LLVMGetParamTypes(this.type, dest);
	}

	/**
	 * Create a new structure type in a context.<br>
	 * A structure is specified by a list of inner elements/types and<br>
	 * whether these can be packed together.
	 */
	// TODO Pointer
	public static TypeRef structTypeInContext(Context c,
			Pointer<LLVMTypeRef> elementTypes, int elementCount, boolean packed) {
		return new TypeRef(LLVMStructTypeInContext(c.context(), elementTypes,
				elementCount, packed ? 1 : 0));
	}

	/**
	 * Create a new structure type in the global context.
	 */
	// TODO Pointer
	public static TypeRef structType(Pointer<LLVMTypeRef> elementTypes,
			int elementCount, boolean packed) {
		return new TypeRef(
				LLVMStructType(elementTypes, elementCount, packed ? 1 : 0));
	}

	/**
	 * Create a new non-packed structure type in the global context.
	 */
	public static TypeRef structType(List<TypeRef> elementTypes) {
		return new TypeRef(LLVMStructType(internalize(elementTypes),
				elementTypes.size(), 0));
	}

	/**
	 * Create an empty identified structure in the global context.
	 */
	public static TypeRef structTypeNamed(String name) {
		return new TypeRef(LLVMStructCreateNamed(LLVMGetGlobalContext(),
				Pointer.pointerToCString(name)));
	}

	/**
	 * Create an empty identified structure in a context.
	 */
	public static TypeRef structTypeNamed(Context c, String name) {
		return new TypeRef(LLVMStructCreateNamed(c.context(),
				Pointer.pointerToCString(name)));
	}

	/**
	 * Set the contents of a structure type.
	 */
	public static void structSetBody(TypeRef struct, List<TypeRef> types,
			boolean packed) {
		LLVMStructSetBody(struct.type, internalize(types), types.size(),
				packed ? 1 : 0);
	}

	/**
	 * Get the number of elements defined inside the structure.
	 */
	public int countStructElementTypes() {
		return LLVMCountStructElementTypes(this.type);
	}

	/**
	 * Get the elements within a structure.<br>
	 * The function is passed the address of a pre-allocated array of<br>
	 * LLVMTypeRef at least LLVMCountStructElementTypes() long. After<br>
	 * invocation, this array will be populated with the structure's<br>
	 * elements. The objects in the destination array will have a lifetime<br>
	 * of the structure type itself, which is the lifetime of the context it<br>
	 * is contained in.
	 */
	public TypeRef [] getStructElementTypes() {
        int n = countStructElementTypes();
        Pointer<LLVMTypeRef> dest = Pointer.allocateTypedPointers(
            LLVMTypeRef.class, n);
        
		LLVMGetStructElementTypes(this.type, dest);
        
        TypeRef [] res = new TypeRef[n];
        int i = 0;
        for (LLVMTypeRef t : dest) {
            res[i++] = new TypeRef(t);
        }
        return res;
	}

	/**
	 * Determine whether a structure is packed.
	 */
	public boolean isPackedStruct() {
		return LLVMIsPackedStruct(this.type) != 0;
	}

	/**
	 * Determine whether a structure is opaque.
	 */
	public boolean isOpaqueStruct() {
		return LLVMIsOpaqueStruct(this.type) != 0;
	}

	/**
	 * Create a fixed size array type that refers to a specific type.<br>
	 * The created type will exist in the context that its element type<br>
	 * exists in.
	 */
	public TypeRef arrayType(int elementCount) {
		return new TypeRef(LLVMArrayType(this.type, elementCount));
	}

	/**
	 * Create a pointer type that points to a defined type.<br>
	 * The created type will exist in the context that its pointee type<br>
	 * exists in.
	 */
	public TypeRef pointerType(int addressSpace) {
		return new TypeRef(LLVMPointerType(this.type, addressSpace));
	}

	/**
	 * Create a pointer type that points to a defined type.<br>
	 * The created type will exist in the context that its pointee type<br>
	 * exists in and the default address space (0).
	 */
	public TypeRef pointerType() {
		return new TypeRef(LLVMPointerType(this.type, 0));
	}

	/**
	 * Create a vector type that contains a defined type and has a specific<br>
	 * number of elements.<br>
	 * The created type will exist in the context thats its element type<br>
	 * exists in.
	 */
	public TypeRef vectorType(int elementCount) {
		return new TypeRef(LLVMVectorType(this.type, elementCount));
	}

	/**
	 * Obtain the type of elements within a sequential type.<br>
	 * This works on array, vector, and pointer types.
	 */
	public TypeRef getElementType() {
		return new TypeRef(LLVMGetElementType(this.type));
	}

	/**
	 * Obtain the length of an array type.<br>
	 * This only works on types that represent arrays.
	 */
	public int getArrayLength() {
		return LLVMGetArrayLength(this.type);
	}

	/**
	 * Obtain the address space of a pointer type.<br>
	 * This only works on types that represent pointers.
	 */
	public int getPointerAddressSpace() {
		return LLVMGetPointerAddressSpace(this.type);
	}

	/**
	 * Obtain the number of elements in a vector type.<br>
	 * This only works on types that represent vectors.
	 */
	public int getVectorSize() {
		return LLVMGetVectorSize(this.type);
	}

	/**
	 * Create a void type in a context.
	 */
	public static TypeRef voidTypeInContext(Context c) {
		return new TypeRef(LLVMVoidTypeInContext(c.context()));
	}

	/**
	 * Create a label type in a context.
	 */
	public static TypeRef labelTypeInContext(Context c) {
		return new TypeRef(LLVMLabelTypeInContext(c.context()));
	}

	public static TypeRef opaqueTypeInContext(Context c) {
		return structTypeNamed(c, null);
	}

	/**
	 * Create a X86 MMX type in a context.
	 */
	public static TypeRef x86MMXTypeInContext(Context c) {
		return new TypeRef(LLVMX86MMXTypeInContext(c.context()));
	}

	/**
	 * These are similar to the above functions except they operate on the<br>
	 * global context.
	 */
	public static TypeRef voidType() {
		return new TypeRef(LLVMVoidType());
	}

	public static TypeRef labelType() {
		return new TypeRef(LLVMLabelType());
	}

	public static TypeRef opaqueType() {
		return structTypeNamed(null);
	}

	public static TypeRef x86MMXType() {
		return new TypeRef(LLVMX86MMXType());
	}

	//public static TypeHandleRef createTypeHandle(LLVMTypeRef PotentiallyAbstractTy);
	//public static void    refineType(LLVMTypeRef AbstractTy, LLVMTypeRef ConcreteTy);
	//public static TypeRef resolveTypeHandle(LLVMTypeHandleRef TypeHandle);
	//public static void    disposeTypeHandle(LLVMTypeHandleRef TypeHandle);

	/**
	 * Obtain a constant value referring to the null instance of a type.
	 */
	public Value constNull() {
		return new Value(LLVMConstNull(this.type));
	}

	/**
	 * Obtain a constant that is a constant pointer pointing to NULL for a<br>
	 * specified type.
	 */
	public Value constPointerNull() {
		return new Value(LLVMConstPointerNull(this.type));
	}

	/**
	 * Obtain a constant value referring to the instance of a type<br>
	 * consisting of all ones.<br>
	 * This is only valid for integer types.
	 */
	public Value constAllOnes() {
		return new Value(LLVMConstAllOnes(this.type));
	}

	/**
	 * Obtain a constant value referring to an undefined value of a type.
	 */
	public Value getUndef() {
		return new Value(LLVMGetUndef(this.type));
	}

	/**
	 * Obtain a constant value for an integer type.<br>
	 * The returned value corresponds to a org.llvm::ConstantInt.<br>
	 * 
	 * @param n
	 *        The value the returned instance should refer to.<br>
	 * @param signExtend
	 *        Whether to sign extend the produced value.
	 */
	public Value constInt(long n, boolean signExtend) {
		return new Value(LLVMConstInt(this.type, n, signExtend ? 1 : 0));
	}

	/**
	 * Obtain a constant value for an integer of arbitrary precision.
	 */
	// TODO: change Pointer to array
	public Value constIntOfArbitraryPrecision(int numWords, Pointer<Long> words) {
		return new Value(LLVMConstIntOfArbitraryPrecision(this.type, numWords,
				words));
	}

	/**
	 * Obtain a constant value for an integer parsed from a string.<br>
	 * A similar API, LLVMConstIntOfStringAndSize is also available. If the<br>
	 * string's length is available, it is preferred to call that function<br>
	 * instead.
	 */
	public Value constIntOfString(String text, byte radix) {
		return new Value(LLVMConstIntOfString(this.type,
				Pointer.pointerToCString(text), radix));
	}

	/**
	 * Obtain a constant value for an integer parsed from a string with<br>
	 * specified length.
	 */
	public Value constIntOfStringAndSize(String text, int sLen, byte radix) {
		return new Value(LLVMConstIntOfStringAndSize(this.type,
				Pointer.pointerToCString(text), sLen, radix));
	}

	/**
	 * Obtain a constant value referring to a double floating point value.
	 */
	public Value constReal(double n) {
		return new Value(LLVMConstReal(this.type, n));
	}

	/**
	 * Obtain a constant for a floating point value parsed from a string.<br>
	 * A similar API, LLVMConstRealOfStringAndSize is also available. It<br>
	 * should be used if the input string's length is known.
	 */
	public Value constRealOfString(String text) {
		return new Value(LLVMConstRealOfString(this.type,
				Pointer.pointerToCString(text)));
	}

	/**
	 * Obtain a constant for a floating point value parsed from a string.
	 */
	public Value constRealOfStringAndSize(String text, int sLen) {
		return new Value(LLVMConstRealOfStringAndSize(this.type,
				Pointer.pointerToCString(text), sLen));
	}

	public Value alignOf(TypeRef ty) {
		return new Value(LLVMAlignOf(this.type));
	}

	public Value sizeOf() {
		return new Value(LLVMSizeOf(this.type));
	}

	public static Pointer<LLVMTypeRef> internalize(TypeRef... types) {
		int n = types.length;
		LLVMTypeRef[] inner = new LLVMTypeRef[n];
		for (int i = 0; i < n; i++) {
			inner[i] = types[i].type;
		}

		Pointer<LLVMTypeRef> array = Pointer.allocateTypedPointers(
				LLVMTypeRef.class, types.length);
		if (array == null) {
			return null;
		}
		array.setArray(inner);

		return array;
	}

	public static Pointer<LLVMTypeRef> internalize(List<TypeRef> types) {
		int n = types.size();
		LLVMTypeRef[] inner = new LLVMTypeRef[n];
		for (int i = 0; i < n; i++) {
			inner[i] = types.get(i).type;
		}

		Pointer<LLVMTypeRef> array = Pointer.allocateTypedPointers(
				LLVMTypeRef.class, types.size());
		if (array == null) {
			return null;
		}
		array.setArray(inner);

		return array;
	}

}
