package dao;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;

public class funcionarioDAO {
    Connection conn; // Represents a connection to the database
    Statement st; // Statement to execute SQL queries
    PreparedStatement stt; // PreparedStatement for precompiled SQL queries
    ResultSet rs; // Stores results from SQL queries

    public boolean conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bdfunc", "root", "");
            st = conn.createStatement();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            return false;
        }
    }

    public boolean salvar(funcionario funcionarioX) {
        String inserir = "INSERT INTO tbFuncionario VALUES ('" + funcionarioX.getRegistro() + "' , '" + funcionarioX.getNome() + "', '" + funcionarioX.getCargo() + "' ," + funcionarioX.getSalario() + ")";
        try {
            st.execute(inserir);
            return true;
        } catch (SQLException ex) {
            return false;
        }
    }

    public void desconectar() {
        try {
            conn.close();
        } catch (SQLException ex) {
            // Handle exception if needed
        }
    }

    public funcionario consultar(String registro) {
        funcionario funcionario = new funcionario();
        try {
            stt = conn.prepareStatement("SELECT * FROM tbFuncionario WHERE registro = ?");
            stt.setString(1, registro);
            rs = stt.executeQuery();
            if (rs.next()) {
                funcionario.setRegistro(rs.getString("registro"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setCargo(rs.getString("cargo"));
                funcionario.setSalario(rs.getDouble("salario"));
                return funcionario;
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

    public boolean editar(funcionario funcionarioX) {
        String inserir = "UPDATE tbFuncionario set nome = '" + funcionarioX.getNome() + "', cargo = '" + funcionarioX.getCargo() + "', salario = " + funcionarioX.getSalario() + " WHERE registro = " + funcionarioX.getRegistro();
        try {
            st.execute(inserir);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }

    public boolean deletar(funcionario funcionarioX) {
        String delet = "DELETE FROM `tbfuncionario` WHERE registro = " + funcionarioX.getRegistro();
        try {
            st.execute(delet);
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
}

