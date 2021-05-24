package project.bzu.groupassignment1.adapters;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;

import java.util.List;

import project.bzu.groupassignment1.R;
import project.bzu.groupassignment1.activities.StudentDetails;
import project.bzu.groupassignment1.models.Student;


public class GetStudentsListAdapter extends RecyclerView.Adapter<GetStudentsListAdapter.ViewHolder> {

    LayoutInflater inflater;
    List<Student> students;

    Context context;

    public GetStudentsListAdapter(Context context, List<Student> students){
        this.inflater=LayoutInflater.from(context);
        this.students=students;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =inflater.inflate(R.layout.student_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.studentName.setText(students.get(position).getName());
        holder.studentGrade.setText(students.get(position).getGrade());
        holder.studentCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent intent = new Intent(context, StudentDetails.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("studentID",students.get(position).getID());
                Log.d("TAG", "onClick: "+students.get(position).getID());
                intent.putExtra("studentName",students.get(position).getName());
                intent.putExtra("studentGender",students.get(position).getGender());
                intent.putExtra("studentDOB",students.get(position).getDOB());
                intent.putExtra("studentGrade",students.get(position).getGrade());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
    return students.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView studentName,studentGrade;
        CardView studentCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            studentName=itemView.findViewById(R.id.student_name);
            studentGrade=itemView.findViewById(R.id.student_grade);
            studentCard=itemView.findViewById(R.id.student_card);

        }
    }
}
