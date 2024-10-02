package com.alan.core_ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.alan.core.R
import com.alan.core.models.Vacancy
import com.alan.core.models.VacancyModel
import com.alan.core.utils.formatDate
import com.alan.core.utils.formatPersonCount
import com.alan.core.utils.formatVacancyCount
import com.alan.core_ui.databinding.ItemButtonBinding
import com.alan.core_ui.databinding.ItemVacancyBinding

class VacanciesAdapter(
    private val clickedMoreVacancyButton: () -> Unit,
    private val clickedVacancy: (id: String) -> Unit,
    private val onLikedClicked: (prevStatus: Boolean, id: String) -> Unit,
) : ListAdapter<VacancyModel, ViewHolder>(VacanciesDiffUtil()) {

    inner class VacanciesViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemVacancyBinding.bind(itemView)

        fun bind(item: Vacancy) {
            binding.apply {
                button.setOnClickListener {}
                root.setOnClickListener {
                    clickedVacancy(currentList[adapterPosition].data?.id ?: "")
                }
                peopleObserve.text = binding.root.context.getString(
                    R.string.observing_people,
                    item.lookingNumber.toString(),
                    formatPersonCount(binding.root.context, item.lookingNumber)
                )
                peopleObserve.isVisible = item.lookingNumber > 0

                if (item.isFavorite)
                    binding.likedIcon.setImageResource(R.drawable.ic_liked_yes)
                else
                    binding.likedIcon.setImageResource(R.drawable.ic_liked_no)

                binding.likedIcon.setOnClickListener {
                    onLikedClicked(item.isFavorite, item.id)
                }

                title.text = item.title
                city.text = item.address.town
                companyName.text = item.company

                experience.text = item.experience.previewText
                publishDate.text = formatDate(binding.root.context, item.publishedDate)
            }
        }
    }

    inner class ButtonViewHolder(itemView: View) : ViewHolder(itemView) {
        private val binding = ItemButtonBinding.bind(itemView)

        fun bind() {
            binding.text.text = binding.root.context.getString(
                R.string.more_vacancy,
                formatVacancyCount(binding.root.context, currentList.size - 1)
            )
            binding.root.setOnClickListener {
                clickedMoreVacancyButton()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == DATA_TYPE) VacanciesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                com.alan.core_ui.R.layout.item_vacancy,
                parent,
                false
            )
        ) else ButtonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                com.alan.core_ui.R.layout.item_button,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is VacanciesViewHolder) holder.bind(getItem(position).data!!)
        else if (holder is ButtonViewHolder) holder.bind()
    }

    override fun getItemViewType(position: Int): Int {
        return if (getItem(position).button) BUTTON_TYPE else DATA_TYPE
    }

    companion object {
        class VacanciesDiffUtil : DiffUtil.ItemCallback<VacancyModel>() {
            override fun areItemsTheSame(oldItem: VacancyModel, newItem: VacancyModel): Boolean {
                return oldItem.data?.id == newItem.data?.id
            }

            override fun areContentsTheSame(oldItem: VacancyModel, newItem: VacancyModel): Boolean {
                return oldItem.data == newItem.data
            }
        }

        const val DATA_TYPE = 1
        const val BUTTON_TYPE = 2
    }
}