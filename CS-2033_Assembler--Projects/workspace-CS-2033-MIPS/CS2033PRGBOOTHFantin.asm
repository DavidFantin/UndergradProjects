.data
     PROMPT1: .ascii "Enter a number to be multiplied (Multipicand): "
     MULTIPLICAND: .space 12
     PROMPT2: .ascii "Enter a number to multiply by (Multiplier): "
     MULTIPLIER: .space 12
     FAIL_MSG_1: .asciiz "Input Error: NON-INTEGER FOUND"
     FAIL_MSG_2: .asciiz "\nInput Error: OVERFLOW"
     NEW_LINE: .asciiz "\n"
     INITIAL_INDENT: .asciiz "\n    "
     INDENT: .asciiz "\n    "
     FINAL_INDENT: .asciiz "    "
     INITIAL_MULTIPLY: .asciiz "  X "
     INITIAL_BAR: .asciiz "  ----------------------------------"
     FINAL_BAR: .asciiz "\n  ------------------------------------------------------------------\n"
     
.text
    MCAND:
          # Ask user for the MULTIPLICAND
          la $a0, PROMPT1
          li $v0, 4
          syscall

   	  # Get the inputed string
          la $a0, MULTIPLICAND
          li $a1, 14
          li $v0, 8
          syscall
          
          j INITIAL_MAIN

    MPLIER:
          # Ask user for the MULTIPLIER
          la $a0, PROMPT2
          li $v0, 4
          syscall

   	  # Get the inputed string
          la $a0, MULTIPLIER
          li $a1, 14
          li $v0, 8
          syscall
          
     INITIAL_MAIN: # CONVERTING MCAND AND MPLIER FROM STINGS TO INTEGERS
     	  addi $t6, $t6, 1 # Number of times INITIAL_MAIN has been called
          addi $t3, $zero, 214748364
          subi $t4, $zero, 214748364
          addi $s2, $zero, 1
          add $s1, $zero, $zero # $s1 = Output
          la $s0, ($a0)
          lb $t0, 0($s0)
          beq $t0, 45, NEGATIVE # Test if it is negative
          add $t2, $zero, $zero
          
     LOOP:         
          addi $t2, $t2, 1 # Counter for how many times the loop runs
          lb $t0, 0($s0)
                 
          beq $t0, 0, TEMPINT # Test if it is null
          beq $t0, 10, TEMPINT # Test if it is a new line
          
          beq $t2, 11, FAIL_2 # If the input goes over the max amount of digits possible
          mul $s1, $s1, 10
          slti $t1, $t0, 48 # Test if character value is less than 0
          beq $t1, 1, FAIL_1 # If it is: FAIL
          slti $t1, $t0, 58 # Test if the value is greater than 9
          beq $t1, 0, FAIL_1 # If it is: FAIL
          sub $t0, $t0, 48
          add $s1, $s1, $t0
          addi $s0, $s0, 1
          
          beq $t2, 9, LENGTH9
          beq $t2, 10, LENGTH10
          
          j LOOP
          
     NEGATIVE:
          subi $s2, $zero, 1
          addi $s0, $s0, 1
          
          j LOOP
          
     LENGTH9: # Places the value of the $s1 @ length of 9 into $t5
     	  add $t5, $s1, $zero
     	  
     	  j LOOP
     
     LENGTH10: # Idea: Compare the output at 9 digits with 214748364
     	  slt $t1, $t5, $t3
          beq $t1, 0, FAIL_2
          slt $t1, $t5, $t4
          beq $t1, 1, FAIL_2
          
          j LOOP
          
     TEMPINT: # Set the integer
     	  beq $t6, 1, SETMCINTEGER
     	  beq $t6, 2, SETMPINTEGER
     	  
     SETMCINTEGER: # Set $s3 = MULTIPLICAND
          add $s3, $s1, $zero
          mul $s3, $s3, $s2
          
          j MPLIER
          
     SETMPINTEGER: # Set $t2 = MULTIPLIER
     	  add $t0, $zero, $zero # $t0 = P the leading bits infront of the MULTIPLIER (the first half of the PRODUCT)
     	  add $t4, $zero, $zero # $t4 = the extra bit at the end
          add $t2, $s1, $zero
          mul $t2, $t2, $s2
          
          la $a0, INITIAL_INDENT    #
          li $v0, 4                 #
          syscall                   # #
                                    # # #
          li $v0, 35                # # # #
          add $a0, $s3, $zero       # # # # # Prints out the MULTIPLICAND
          syscall                   # # # # 
                                    # # #
          la $a0, NEW_LINE          # #
          li $v0, 4                 #
          syscall                   #
          
          la $a0, INITIAL_MULTIPLY  #
          li $v0, 4                 #
          syscall                   # #
                                    # # #
          li $v0, 35                # # # #
          add $a0, $t2, $zero       # # # # # Prints out the INITIAL MULTIPLIER
          syscall                   # # # # 
                                    # # #
          la $a0, NEW_LINE          # #
          li $v0, 4                 #
          syscall                   #
          
          la $a0, INITIAL_BAR
          li $v0, 4
          syscall
          
     OP_MAIN: # THE OPERATIONS OF BOOTH'S ALGORITHM
          addi $t7, $t7, 1 # Step Counter
          beq $t7, 33, OUTPUT # END CONDITION ################################################
          
          andi $t3, $t2, 1 # $t3 = LSB of MULTIPLIER PRE-SHIFT
          
          j OPCHECK
          
     SHIFT_MAIN: # THE SHIFTING OF BOOTH'S ALGORITHM
          andi $t1, $t0, 1 # $t1 = the bit that will be shifted into the MULTIPLIER
          sll $t1, $t1, 31 # ADD TO MPLIER AFTER SHIFT (makes the LSB of P into the MSB of an empty register)
          
          add $t4, $t3, $zero # Sets the new EXTRA BIT value
          
          sra $t0, $t0, 1 # Shift P to the right by 1 (leave a copy of the MSB at the front)
          srl $t2, $t2, 1 # Shift MPLIER to the right by 1
          add $t2, $t2, $t1 # Simulates LSB of P shifting into the MSB spot of the MPLIER
              
          la $a0, INDENT
          li $v0, 4
          syscall
          
     	  li $v0, 35
          add $a0, $t0, $zero
          syscall
                    
          li $v0, 35
          add $a0, $t2, $zero
          syscall

          j OP_MAIN
     
     OPCHECK: # Checks to see if MCAND needs to be added or subtracted
           beq $t3, 0, ADDCHECK
           beq $t3, 1, SUBCHECK
           
     ADDCHECK:
           beq $t4, 1, ADD
           beq $t4, 0, SHIFT_MAIN

     SUBCHECK:
           beq $t4, 0, SUB
           beq $t4, 1, SHIFT_MAIN
 
     ADD: # When 0,1; P += MCAND
           add $t0, $t0, $s3
           
           j SHIFT_MAIN
     
     SUB: # When 1,0; P -= MCAND
           sub $t0, $t0, $s3
           
           j SHIFT_MAIN    
          
     FAIL_1: # Non-integer error
     	  la $a0, FAIL_MSG_1
          li $v0, 4
          syscall
     	  
     	  j EXIT
          
     FAIL_2: # Overflow error
          la $a0, FAIL_MSG_2
          li $v0, 4
          syscall
     	  
     	  j EXIT
     	  
     OUTPUT:
     	  la $a0, FINAL_BAR
          li $v0, 4
          syscall

     	  la $a0, FINAL_INDENT
          li $v0, 4
          syscall
          
     	  li $v0, 35          #
          add $a0, $t0, $zero # # Prints out the FINAL P
          syscall             #
          
          li $v0, 35          #
          add $a0, $t2, $zero # # Prints out the FINAL MPLIER
          syscall             #
          
     EXIT:
