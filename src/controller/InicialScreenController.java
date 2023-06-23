package controller;

import java.awt.BorderLayout;
import java.awt.event.*;

import View.TelaInicialGUI;
import database.ContatoDao;
import database.createList.DoublyLinkedLists.ContatoList;
import database.createList.DoublyLinkedLists.ContControlList;
import database.createList.NOs.ContatoNO;
import database.createList.NOs.ContControlNO;

public class InicialScreenController implements ActionListener, MouseListener {
    private ContControlList listaContatoController;
    private ContatoList listaContatos;
    private TelaInicialGUI telaInicialGUI;
    private int countMouseClicked;
    private boolean mouseClicked;

    public InicialScreenController() {
        this.listaContatos = new ContatoDao().Listar();
        this.listaContatoController = new ContControlList();
        this.telaInicialGUI = new TelaInicialGUI();
        this.countMouseClicked = 0;

        PreencheContatos();

        updateInterface();

        getTelaInicialGUI().add(telaInicialGUI.getScrollPane(), BorderLayout.CENTER);
        getTelaInicialGUI().setVisible(true);
    }

    public void updateInterface() {
        getTelaInicialGUI().getPanelContatos().removeAll();
        addContatoPanel();
        addListener();
        getTelaInicialGUI().getPessoaController().setInicialScreenController(this);
        getTelaInicialGUI().getFiltro().setInicialScreenController(this);
        getTelaInicialGUI().getCategoriaController().setInicialScreenController(this);
    }

    public void addListener() {
        addMouseListenerContatos();
        ListenerButtons();

    }

    public void PreencheContatos() {
        ContatoNO current = getListaContatos().getInicio();
        while (current != null) {
            getListaContatoController().InsereNoFim(new ContatoController(current.getContato()));
            current = current.getProximo();
        }
    }

    public void addContatoPanel() {
        ContControlNO current = getListaContatoController().getInicio();
        while (current != null) {
            getTelaInicialGUI().getPanelContatos().add(current.getContato().getContatoGUI());
            current = current.getProximo();
        }
    }

    private void addMouseListenerContatos() {
        ContControlNO current = getListaContatoController().getInicio();
        setMouseClicked(true);
        while (current != null) {
            current.getContato().setInicialScreenController(this);
            current.getContato().getContatoGUI().addMouseListener(this);
            current = current.getProximo();
        }

    }

    private void mouseListenerContatos() {

        if (!getTelaInicialGUI().getPessoaController().getPessoaGUI().isVisible() && getCountMouseClicked() == 1) {
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(true);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().getAdicionarCategoria().setVisible(true);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().getRemoverCategoria().setVisible(true);
            getTelaInicialGUI().getCriar().setVisible(false);
            getTelaInicialGUI().getEditar().setVisible(true);
            getTelaInicialGUI().getApagar().setVisible(true);
            getTelaInicialGUI().getApagarTodos().setVisible(false);

        } else if (getCountMouseClicked() == 1
                && getTelaInicialGUI().getPessoaController().getPessoaGUI().isVisible()) {
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().getAdicionarCategoria().setVisible(true);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().getRemoverCategoria().setVisible(true);
            getTelaInicialGUI().getCriar().setVisible(false);
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(false);

        } else if (getCountMouseClicked() == 0) {
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(false);
            getTelaInicialGUI().getAdicionarCategoria().setVisible(false);
            getTelaInicialGUI().getRemoverCategoria().setVisible(false);
            getTelaInicialGUI().getCriar().setVisible(true);

        } else if (getCountMouseClicked() > 1
                && !getTelaInicialGUI().getPessoaController().getPessoaGUI().isVisible()) {
            getTelaInicialGUI().getAdicionarCategoria().setVisible(true);
            getTelaInicialGUI().getRemoverCategoria().setVisible(true);
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(true);
            getTelaInicialGUI().getCriar().setVisible(false);

        }
    }

    private void ListenerButtons() {
        getTelaInicialGUI().getCriar().addActionListener(e -> {
            getTelaInicialGUI().getCriar().setVisible(false);
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(false);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(false);
            getTelaInicialGUI().getPessoaController().setNovoContato();
            getTelaInicialGUI().getPessoaController().getPessoaGUI().setVisible(true);
            setMouseClicked(false);
        });
        getTelaInicialGUI().getEditar().addActionListener(e -> {
            getTelaInicialGUI().getCriar().setVisible(false);
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(false);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(false);
            getTelaInicialGUI().getPessoaController().setEditarContato(getContatoSelect());
            getTelaInicialGUI().getPessoaController().getPessoaGUI().setVisible(true);
            setMouseClicked(false);
        });
        getTelaInicialGUI().getApagar().addActionListener(e -> {
            getTelaInicialGUI().getPessoaController().apaga();
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(false);
            getTelaInicialGUI().getAdicionarCategoria().setVisible(false);
            getTelaInicialGUI().getRemoverCategoria().setVisible(false);
            getTelaInicialGUI().getCriar().setVisible(true);

            updateInterface();
        });
        getTelaInicialGUI().getApagarTodos().addActionListener(e -> {
            getTelaInicialGUI().getPessoaController().apaga();
            getTelaInicialGUI().getEditar().setVisible(false);
            getTelaInicialGUI().getApagar().setVisible(false);
            getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(false);
            getTelaInicialGUI().getApagarTodos().setVisible(false);
            getTelaInicialGUI().getAdicionarCategoria().setVisible(false);
            getTelaInicialGUI().getRemoverCategoria().setVisible(false);
            getTelaInicialGUI().getCriar().setVisible(true);

            updateInterface();
        });

        getTelaInicialGUI().getPessoaController().getPessoaGUI().getCancelar().addActionListener(e -> {
            if (getCountMouseClicked() >= 1) {
                getTelaInicialGUI().getCriar().setVisible(false);
                getTelaInicialGUI().getEditar().setVisible(true);
                getTelaInicialGUI().getApagar().setVisible(true);
                getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(true);
                System.out.println("a");
            } else {
                getTelaInicialGUI().getCriar().setVisible(true);
                getTelaInicialGUI().getEditar().setVisible(false);
                getTelaInicialGUI().getApagar().setVisible(false);
                getTelaInicialGUI().getCategoriaController().getCategoriaGUI().setVisible(false);
            }
            getTelaInicialGUI().getApagarTodos().setVisible(false);
            getTelaInicialGUI().getPessoaController().getPessoaGUI().setVisible(false);
            setMouseClicked(true);

        });
    }

    public void setMouseClicked(boolean aFlag) {
        this.mouseClicked = aFlag;
        ContControlNO current = getListaContatoController().getInicio();
        while (current != null) {
            current.getContato().setMouseClicked(aFlag);
            current = current.getProximo();
        }
    }

    public ContatoController getContatoSelect() {
        ContControlNO current = getListaContatoController().getInicio();
        while (current != null) {
            if (current.getContato().isSelect()) {
                return current.getContato();
            }
            current = current.getProximo();
        }
        return null;
    }

    public String[] getContatosSelects() {
        ContControlNO current = getListaContatoController().getInicio();
        StringBuilder sb = new StringBuilder();
        while (current != null) {
            sb.append(current.getContato().getContato().getId()).append(" - ");
            current = current.getProximo();
        }
        return String.valueOf(sb).split(" - ");
    }

    public ContControlList getListaContatoController() {
        return listaContatoController;
    }

    public void setListaContatoController(ContControlList listaContatoController) {
        this.listaContatoController = listaContatoController;
    }

    public ContatoList getListaContatos() {
        return listaContatos;
    }

    public void setListaContatos(ContatoList listaContatos) {
        this.listaContatos = listaContatos;
    }

    public TelaInicialGUI getTelaInicialGUI() {
        return telaInicialGUI;
    }

    public void setTelaInicialGUI(TelaInicialGUI telaInicialGUI) {
        this.telaInicialGUI = telaInicialGUI;
    }

    public int getCountMouseClicked() {
        return countMouseClicked;
    }

    public void setCountMouseClicked(int countMouseClicked) {
        System.out.println(countMouseClicked);
        this.countMouseClicked = countMouseClicked;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (isMouseClicked()) {
            mouseListenerContatos();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public boolean isMouseClicked() {
        return mouseClicked;
    }

}