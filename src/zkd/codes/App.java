package zkd.codes;

import zkd.codes.dao.CostumerMapDAO;
import zkd.codes.dao.ICostumerDAO;
import zkd.codes.domain.Costumer;

import javax.swing.*;


public class App {
    public static ICostumerDAO iCustomerDAO;

    public static void main(String[] args){
        iCustomerDAO = new CostumerMapDAO();

        String answer = JOptionPane.showInputDialog(null, "Digite um número: 1 - Cadastro | 2 - Consulta | 3 - Alteração | 4 - Remoção | 5 - Fechar Programa", "Opções", JOptionPane.INFORMATION_MESSAGE);

        while(!isValidOption(answer)){
            if("".equals(answer)){
                Integer result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
                if(result.equals(JOptionPane.YES_OPTION)){
                    close();
                }
            }
            JOptionPane.showMessageDialog(null, "Você digitou um valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            answer = JOptionPane.showInputDialog(null, "Digite um número: 1 - Cadastro | 2 - Consulta | 3 - Alteração | 4 - Remoção | 5 - Fechar Programa", "Opções", JOptionPane.INFORMATION_MESSAGE);
        }

        while(isValidOption(answer)){
            if(isRegister(answer)){
                String data = JOptionPane.showInputDialog(null, "Digite os dados: nome, cpf, telefone, endereço, número endereço, cidade, estado.", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                register(data);
            } else if(isSearch(answer)){
                String cpf = JOptionPane.showInputDialog(null, "Digite o CPF do cliente para consulta-lo.", "Consulta", JOptionPane.INFORMATION_MESSAGE);
                search(cpf);
            } else if(isUpdate(answer)){
                Integer result = JOptionPane.showConfirmDialog(null, "Não é possível alterar o CPF de um cliente, mas caso seja necessário, exclua o cliente da lista e o cadastre novamente! Deseja continuar?", "Aviso", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
                if(result.equals(JOptionPane.YES_OPTION)){
                    String cpf = JOptionPane.showInputDialog(null, "Digite o CPF do cliente para alterar-lo.", "Alteração", JOptionPane.INFORMATION_MESSAGE);
                    update(cpf);
                }
            } else if(isDelete(answer)){
                String cpf = JOptionPane.showInputDialog(null, "Digite o CPF do cliente para remove-lo.", "Remover", JOptionPane.INFORMATION_MESSAGE);
                delete(cpf);
            } else if(isClose(answer)){
                Integer result = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja sair?", "Sair", JOptionPane.YES_NO_OPTION);
                if(result.equals(JOptionPane.YES_OPTION)){
                    close();
                }
            }

            answer = JOptionPane.showInputDialog(null, "Digite um número: 1 - Cadastro | 2 - Consulta | 3 - Alteração | 4 - Remoção | 5 - Fechar Programa", "Opções", JOptionPane.INFORMATION_MESSAGE);
        }
    }


    private static boolean isValidOption(String answer) {
        try{
            int number = Integer.parseInt(answer);
            if(number >= 1 && number <= 5){
                return true;
            }
            return false;
        }catch (Exception e){
            return false;
        }
    }

    private static void close() {
        JOptionPane.showMessageDialog(null, "Até logo!", "Sair" , JOptionPane.INFORMATION_MESSAGE);
        System.exit(0);
    }

    private static boolean isRegister(String answer) {
        if("1".equals(answer)){
            return true;
        }
        return false;
    }

    private static void register(String data) {
        String[] cutData = data.split(",");
        if(cutData.length != 7){
            JOptionPane.showMessageDialog(null, "Deve se preencher os 7 itens citados anteriormente! Por favor, reinicie o programa.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        for(String information : cutData){
            if(information == "" || information == null){
                JOptionPane.showMessageDialog(null, "Faltaram dados a serem preenchidos! Por favor, reinicie o programa.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
        String[] dataWithoutSpaces = new String[7];
        for(int i = 0; i < 7; i++){
            dataWithoutSpaces[i] = cutData[i].trim().toUpperCase();
        }
        Boolean isNumbers = hasNumbers(dataWithoutSpaces);
        if(isNumbers.equals(true)){
            Costumer object = new Costumer(dataWithoutSpaces[0], dataWithoutSpaces[1], dataWithoutSpaces[2], dataWithoutSpaces[3], dataWithoutSpaces[4], dataWithoutSpaces[5], dataWithoutSpaces[6]);
            boolean result = iCustomerDAO.create(object);
            if(result){
                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Houve um erro no cadastro! Por favor, reinicie o programa.", "Erro", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        } else{
            JOptionPane.showMessageDialog(null, "Você digitou letras em campos que requisitam números! Por conta disso, o cliente não foi cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static boolean hasNumbers(String[] data){
        try{
            Long cpf = Long.parseLong(data[1]);
            Long phoneNumber = Long.parseLong(data[2]);
            Long addressNumber = Long.parseLong(data[4]);
            return true;
        } catch (Exception e){
            return false;
        }
    }


    private static boolean isSearch(String answer) {
        if("2".equals(answer)){
            return true;
        }
        return false;
    }

    private static void search(String cpf){
        Costumer object = iCustomerDAO.search(Long.valueOf(cpf));
        if(object != null){
            JOptionPane.showMessageDialog(null, object.toString(), "Consulta", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "O CPF digitado é inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isUpdate(String answer) {
        if("3".equals(answer)){
            return true;
        }
        return false;
    }

    private static void update(String cpf){
        Costumer object = iCustomerDAO.search(Long.valueOf(cpf));
        if(object != null){
            String data = JOptionPane.showInputDialog(null,"Digite os novos dados: nome, telefone, endereço, número endereço, cidade, estado.", "Alteração", JOptionPane.INFORMATION_MESSAGE);
            String[] cutData = data.split(",");
            if(cutData.length != 6){
                JOptionPane.showMessageDialog(null, "Deve se preencher os 6 itens citados anteriormente! Por favor, reinicie o programa.", "Erro", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            for(int i = 0; i < cutData.length; i++){
                if(cutData[i] == "" || cutData[i] == null){
                    JOptionPane.showMessageDialog(null, "Faltaram dados a serem preenchidos! Por favor, reinicie o programa.", "Erro", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }
            }
            String[] dataWithoutSpaces = new String[6];
            for(int i = 0; i < 6; i++){
                dataWithoutSpaces[i] = cutData[i].trim().toUpperCase();
            }
            Boolean isNumbers = hasNumbersInUpdate(dataWithoutSpaces);
            if(isNumbers.equals(true)){
                Costumer newObjectData = new Costumer(dataWithoutSpaces[0], cpf, dataWithoutSpaces[1], dataWithoutSpaces[2], dataWithoutSpaces[3], dataWithoutSpaces[4], dataWithoutSpaces[5]);
                iCustomerDAO.update(newObjectData);
                JOptionPane.showMessageDialog(null, "A alteração dos dados do cliente ocorreram com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(null, "Você digitou letras em campos que requisitam números! Por conta disso, os dados do cliente não foram alterados.", "Erro", JOptionPane.ERROR_MESSAGE);
            }

        }else{
            JOptionPane.showMessageDialog(null, "O CPF digitado é inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static Boolean hasNumbersInUpdate(String[] data) {
        try{
            Long phoneNumber = Long.parseLong(data[1]);
            Long addressNumber = Long.parseLong(data[3]);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    private static boolean isDelete(String answer) {
        if("4".equals(answer)){
            return true;
        }
        return false;
    }

    private static void delete(String cpf){
        Costumer object = iCustomerDAO.search(Long.valueOf(cpf));
        if(object != null){
            iCustomerDAO.delete(object.getCpf());
            JOptionPane.showMessageDialog(null, "Cliente removido com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "O CPF digitado é inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isClose(String answer) {
        if("5".equals(answer)){
            return true;
        }
        return false;
    }
}
