package com.monyrama.ui.components;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

/**
 *
 * @author John
 */
public class AutosuggestTextFieldLimited extends JPanel {

    private JComboBox comboBox;
    private JTextFieldLimited textFieldLimited;
    private SuggestionProvider suggestionProvider;
    private ActionListener enterKeyListener;

    private String currentText;

    /**
     * Creates new form AutosuggestTextFieldLimited
     */
    public AutosuggestTextFieldLimited(int maxLength) {
        this.suggestionProvider = new SuggestionProvider() {
            @Override
            public List<String> getValues(String text) {
                return new ArrayList<String>(0);
            }
        };
        this.enterKeyListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            }
        };

        setLayout(new GridBagLayout());

        comboBox = new JComboBox();
        textFieldLimited = new JTextFieldLimited(maxLength);

        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;

        add(textFieldLimited, gridBagConstraints);
        add(comboBox, gridBagConstraints);

        final KeyListener keyListener = new KeyAdapter() {
            @Override
            public void keyReleased(final KeyEvent keyEvent) {
                SwingUtilities.invokeLater(new Runnable() {
                    public void run() {
                        if(keyEvent.getKeyCode() == KeyEvent.VK_DOWN) {
                            if(comboBox.getItemCount() == 0) {
                                return;
                            }
                            int selectedIndex = comboBox.getSelectedIndex();
                            if(selectedIndex < comboBox.getItemCount() - 1) {
                                comboBox.setSelectedIndex(selectedIndex + 1);
                            } else {
                                comboBox.setSelectedIndex(0);
                            }
                            AutosuggestTextFieldLimited.this.repaint();
                        } else if(keyEvent.getKeyCode() == KeyEvent.VK_UP) {
                            if(comboBox.getItemCount() == 0) {
                                return;
                            }
                            int selectedIndex = comboBox.getSelectedIndex();
                            if(selectedIndex > 0) {
                                comboBox.setSelectedIndex(selectedIndex - 1);
                            } else {
                                comboBox.setSelectedIndex(comboBox.getItemCount() - 1);
                            }
                            AutosuggestTextFieldLimited.this.repaint();
                        } else {
                            if(textFieldLimited.getText().equals(currentText)) {
                                return;
                            }
                            currentText = textFieldLimited.getText();
                            comboFilter(textFieldLimited.getText());
                            AutosuggestTextFieldLimited.this.repaint();
                        }
                    }
                });
            }
        };

        textFieldLimited.addKeyListener(keyListener);

        //for key item selection
        textFieldLimited.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBox.getItemCount() == 0 || comboBox.getSelectedItem() == null) {
                    enterKeyListener.actionPerformed(e);
                    return;
                }
                itemSelectedAction();
            }
        });

        //for mouse item selection
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean mouseClicked = ((e.getModifiers() & InputEvent.BUTTON1_MASK) == InputEvent.BUTTON1_MASK)
                        || ((e.getModifiers() & InputEvent.BUTTON3_MASK) == InputEvent.BUTTON3_MASK);

                if(mouseClicked && comboBox.getSelectedItem() != null) {
                    itemSelectedAction();
                }
            }
        });

    }

    private void itemSelectedAction() {
        textFieldLimited.setText(comboBox.getSelectedItem().toString());
        comboBox.hidePopup();
        comboBox.removeAllItems();
        AutosuggestTextFieldLimited.this.repaint();
        currentText = textFieldLimited.getText();
    }

    private void comboFilter(String enteredText) {
        List<String> filterArray = suggestionProvider.getValues(enteredText);

        if (filterArray.size() > 0) {
            comboBox.setModel(new DefaultComboBoxModel(filterArray.toArray()));
            comboBox.setSelectedIndex(-1);
            comboBox.showPopup();
        } else {
            comboBox.removeAllItems();
            comboBox.hidePopup();
        }
    }

    public void setText(String text) {
        textFieldLimited.setText(text);
    }

    public String getText() {
        return textFieldLimited.getText();
    }

    public SuggestionProvider getSuggestionProvider() {
        return suggestionProvider;
    }

    public void setSuggestionProvider(SuggestionProvider suggestionProvider) {
        this.suggestionProvider = suggestionProvider;
    }

    public ActionListener getEnterKeyListener() {
        return enterKeyListener;
    }

    public void setEnterKeyListener(ActionListener enterKeyListener) {
        this.enterKeyListener = enterKeyListener;
    }

    @Override
    public void setPreferredSize(Dimension preferredSize) {
        super.setPreferredSize(preferredSize);
        comboBox.setPreferredSize(preferredSize);
        textFieldLimited.setPreferredSize(preferredSize);
    }

    public interface SuggestionProvider {
        List<String> getValues(String text);
    }
}


