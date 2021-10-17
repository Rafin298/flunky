package ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app1.R;
import com.example.app1.todoList.todo3;

import java.util.List;

import model.TodoListModel;

public class TodoListRecyclerAdapter extends RecyclerView.Adapter<TodoListRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<TodoListModel> myDoes;

    public TodoListRecyclerAdapter(Context context, List<TodoListModel> todoList) {
        this.context = context;
        this.myDoes = todoList;
    }

    @NonNull
    @Override
    public TodoListRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.todo_row, viewGroup, false);

        return new TodoListRecyclerAdapter.ViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoListRecyclerAdapter.ViewHolder viewHolder, int position) {
        TodoListModel todoList = myDoes.get(position);
        viewHolder.titledoes.setText(todoList.getTitledoes());
        viewHolder.descdoes.setText(todoList.getDescdoes());
        viewHolder.datedoes.setText(todoList.getDatedoes());

        final String getTitleDoes = todoList.getTitledoes();
        final String getDescDoes = todoList.getDescdoes();
        final String getDateDoes = todoList.getDatedoes();
        final String getKeyDoes = todoList.getKeydoes();

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aa = new Intent(context, todo3.class);
                aa.putExtra("titledoes", getTitleDoes);
                aa.putExtra("descdoes", getDescDoes);
                aa.putExtra("datedoes", getDateDoes);
                aa.putExtra("keydoes", getKeyDoes);
                context.startActivity(aa);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myDoes.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView titledoes, descdoes, datedoes, keydoes;


        public ViewHolder(@NonNull View itemView, Context ctx) {
            super(itemView);


            titledoes = (TextView) itemView.findViewById(R.id.todoTitle);
            descdoes = (TextView) itemView.findViewById(R.id.todoDes);
            datedoes = (TextView) itemView.findViewById(R.id.todoDate);

        }
    }
}
