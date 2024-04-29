package safetycalculator;

import safetycalculator.controller.CalculationController;

public class MainApplication {
    public static void main(String[] args) {
        CalculationController controller = new CalculationController();
        
        controller.startApplication();
    }
}